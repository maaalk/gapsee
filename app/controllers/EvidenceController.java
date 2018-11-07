package controllers;

import io.ebean.enhance.common.SysoutMessageOutput;
import io.ebean.migration.util.IOUtils;
import models.Badge;
import models.BadgeStatus;
import models.Evidence;

import models.EvidenceStatus;
import org.h2.store.fs.FileUtils;
import play.api.mvc.MultipartFormData;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import utils.SaveUpload;
import views.html.evidence.evidenceevaluate;
import views.html.evidence.evidencenew;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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
            evidence.setDate(calendar.getTime());
            evidence.setFileName(evidenceFile.getFilename());

            SaveUpload saveFile = new SaveUpload(evidenceFile.getFile(), evidence);
            evidence.setFilePath(saveFile.trasnformFile());
            evidence.setStatus(EvidenceStatus.NEW);
            evidence.save();
            evidence.getBadge().update();
            flash("success","Evidence Saved");
            return redirect(routes.BadgeController.show(evidence.getBadge().getId()));
        }else {
            flash("error", "Missing file");
            return badRequest();
        }
    }

    public Result evaluate (Integer evidenceId){
        Evidence evidence = Evidence.find.byId(evidenceId);
        return ok(evidenceevaluate.render(evidence));
    }

    public Result evaluationResult(Integer evidenceId){
        Evidence evidence = formFactory.form(Evidence.class).bindFromRequest().get();
        Evidence oldEvidence = Evidence.find.byId(evidenceId);
        if (oldEvidence == null){
            return notFound("Badge not found");
        }
        DynamicForm requestData = formFactory.form().bindFromRequest();
        System.out.println(requestData.get("enumStatus"));

        if (requestData.get("enumStatus").equals("ACCEPTED")){
            System.out.println("true");
            oldEvidence.setStatus(EvidenceStatus.ACCEPTED);
        }
        if (requestData.get("enumStatus").equals("REJECTED")){
            oldEvidence.setStatus(EvidenceStatus.REJECTED);
        }
        oldEvidence.setFeedback(evidence.getFeedback());
        oldEvidence.getBadge().update();
        oldEvidence.update();
        return redirect(routes.BadgeController.show(oldEvidence.getBadge().getId()));


    }



}
