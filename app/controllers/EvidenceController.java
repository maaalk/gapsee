package controllers;

import models.*;

import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import play.mvc.Security;
import utils.ActionAuthenticator;
import utils.FileManager;
import utils.TutorActionAuthenticator;
import views.html.evidence.evidenceedit;
import views.html.evidence.evidenceevaluate;
import views.html.evidence.evidencenew;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Security.Authenticated(ActionAuthenticator.class)
public class EvidenceController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result create(Integer badgeId){
        Form<Evidence> evidenceForm= formFactory.form(Evidence.class);
        Badge badge = Badge.find.byId(badgeId);
        return ok(evidencenew.render(evidenceForm,badge));
    }

    public Result save(Integer badgeId) throws IOException {
        Form<Evidence> evidenceForm = formFactory.form(Evidence.class).bindFromRequest();
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> evidenceFile = body.getFile("evidenceFile");
        if (evidenceFile != null) {
            Evidence evidence = evidenceForm.get();
            evidence.setBadge(Badge.find.byId(badgeId));
            Calendar calendar = Calendar.getInstance();
            evidence.setSubmissionDate(calendar.getTime());
            evidence.setFeedbackDate(calendar.getTime());
            evidence.setFileName(evidenceFile.getFilename());

            evidence.setFilePath(FileManager.savaFile(evidenceFile.getFile(),evidence));

            evidence.setStatus(EvidenceStatus.NEW);
            evidence.save();
            User user = User.find.query()
                    .where().eq("username",session("username"))
                    .findOne();

            UserBadge userBadge = new UserBadge(user,evidence.getBadge());
            userBadge.save();
            user.addUserBadge(userBadge);
            user.update();
            evidence.getBadge().addUserBadge(userBadge);
            evidence.getBadge().update();
            flash("success","Evidence Saved");
            return redirect(routes.BadgeController.show(evidence.getBadge().getId()));
        }else {
            flash("fail", "Missing file");
            return badRequest();
        }
    }

    public Result edit(Integer id){
        Evidence evidence = Evidence.find.byId(id);
        if (evidence==null){
            flash("fail","Sorry, I can't do this =/");
            return notFound("Badge not found");
        }
        return  ok(evidenceedit.render(evidence,evidence.getBadge()));
    }

    public Result update(Integer id){
        Evidence evidence = formFactory.form(Evidence.class).bindFromRequest().get();
        Evidence oldEvidence = Evidence.find.byId(id);

        if (oldEvidence == null){
            flash("fail","Sorry, I can't do this =/");
            return notFound("Badge not found");
        }

        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> evidenceFile = body.getFile("evidenceFile");

        if (!evidenceFile.getFilename().isEmpty()) {
            System.out.println("file not null: "+evidenceFile.getFilename());
            oldEvidence.setFileName(evidenceFile.getFilename());
            oldEvidence.setFilePath(FileManager.savaFile(evidenceFile.getFile(),oldEvidence));
        }

        oldEvidence.setDescription(evidence.getDescription());
        //get current date
        Calendar calendar = Calendar.getInstance();
        oldEvidence.setSubmissionDate(calendar.getTime());
        oldEvidence.update();
        flash("success","Evidence updated =D");
        return redirect(routes.BadgeController.show(oldEvidence.getBadge().getId()));


    }
    @Security.Authenticated(TutorActionAuthenticator.class)
    public Result evaluate (Integer evidenceId){
        Evidence evidence = Evidence.find.byId(evidenceId);
        return ok(evidenceevaluate.render(evidence));
    }
    @Security.Authenticated(TutorActionAuthenticator.class)
    public Result evaluationResult(Integer evidenceId){
        Evidence evidence = formFactory.form(Evidence.class).bindFromRequest().get();
        Evidence oldEvidence = Evidence.find.byId(evidenceId);

        if (oldEvidence == null){
            return notFound("Badge not found");
        }

        DynamicForm requestData = formFactory.form().bindFromRequest();

        if (requestData.get("enumStatus").equals("ACCEPTED")){
            oldEvidence.setStatus(EvidenceStatus.ACCEPTED);
        }
        if (requestData.get("enumStatus").equals("REJECTED")){
            oldEvidence.setStatus(EvidenceStatus.REJECTED);
        }

        oldEvidence.setFeedback(evidence.getFeedback());
        oldEvidence.getBadge().update();
        //get current date
        Calendar calendar = Calendar.getInstance();
        oldEvidence.setFeedbackDate(calendar.getTime());
        oldEvidence.update();
        flash("success","Evaluation registered!");

        return redirect(routes.BadgeController.show(oldEvidence.getBadge().getId()));


    }



    public Result evidenceDownload(Integer evidenceId){

        Evidence evidence = Evidence.find.byId(evidenceId);

        return ok(FileManager.openFile(evidence.getFilePath()));
    }




}
