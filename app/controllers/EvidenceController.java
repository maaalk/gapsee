package controllers;

import io.ebean.enhance.common.SysoutMessageOutput;
import io.ebean.migration.util.IOUtils;
import models.Badge;
import models.Evidence;

import org.h2.store.fs.FileUtils;
import play.api.mvc.MultipartFormData;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import utils.SaveUpload;
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

            evidence.save();
            flash("success","Evidence Saved");
            return redirect(routes.BadgeController.show(evidence.getBadge().getId()));
        }else {
            flash("error", "Missing file");
            return badRequest();
        }
    }


    public Result upload() {
        Form<Evidence> evidenceForm = formFactory.form(Evidence.class).bindFromRequest();
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");
        if (picture != null) {
            Evidence evidence = evidenceForm.get();

            evidence.save();
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }



}
