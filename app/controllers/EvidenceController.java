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
import views.html.evidence.evidencelog;
import views.html.evidence.evidencenew;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
            Calendar calendar = Calendar.getInstance();
            Evidence evidence = evidenceForm.get();
            evidence.setSubmissionDate(calendar.getTime());
            evidence.setFeedbackDate(calendar.getTime());
            evidence.setFileName(evidenceFile.getFilename());
            evidence.setFilePath(FileManager.savaFile(evidenceFile.getFile(),evidence));
            User user = User.findByUserName(session("username"));
            Badge badge = Badge.find.byId(badgeId);
            UserBadge userBadge = UserBadge.findUserBadge(user.getId(),badge.getId());
            if (userBadge==null){
                userBadge = new UserBadge(user,badge);
                userBadge.save();
                user.addUserBadge(userBadge);
                user.update();
                badge.addUserBadge(userBadge);
                badge.update();
            }
            evidence.setUserBadge(userBadge);
            evidence.setStatus(EvidenceStatus.NEW);
            evidence.save();
            userBadge.update();
            flash("success","Evidence Saved");
            return redirect(routes.BadgeController.show(badge.getId()));
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
        return  ok(evidenceedit.render(evidence,evidence.getUserBadge().getBadge()));
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
        oldEvidence.getUserBadge().setLastUpdate(calendar.getTime());
        oldEvidence.getUserBadge().update();
        flash("success","Evidence updated =D");
        return redirect(routes.BadgeController.show(oldEvidence.getUserBadge().getBadge().getId()));
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
        oldEvidence.getUserBadge().update();
        //get current date
        Calendar calendar = Calendar.getInstance();
        oldEvidence.setFeedbackDate(calendar.getTime());
        oldEvidence.update();
        flash("success","Evaluation registered!");

        return redirect(routes.BadgeController.tutorShow(oldEvidence.getUserBadge().getBadge().getId()));


    }



    public Result evidenceDownload(Integer evidenceId){

        Evidence evidence = Evidence.find.byId(evidenceId);

        return ok(FileManager.openFile(evidence.getFilePath()));
    }


    public Result evidenceLog(Integer userBadgeId){
        UserBadge userBadge = UserBadge.find.byId(userBadgeId);
        return ok(evidencelog.render(userBadge.getBadge(), userBadge.getPreviousEvidences(),userBadge.getLastEvidence()));
    }


}
