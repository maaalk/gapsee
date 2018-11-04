package controllers;

import io.ebean.enhance.common.SysoutMessageOutput;
import models.Badge;
import models.Evidence;
import play.api.mvc.MultipartFormData;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.evidence.evidencenew;

import javax.inject.Inject;
import java.io.File;
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

    public Result save(Integer badgeId){
        Form<Evidence> evidenceForm = formFactory.form(Evidence.class).bindFromRequest();
        Evidence evidence = evidenceForm.get();
        evidence.setBadge(Badge.find.byId(badgeId));
        Calendar calendar = Calendar.getInstance();
        evidence.setDate(calendar.getTime());
        evidence.save();
        return redirect(routes.BadgeController.show(evidence.getBadge().getId()));
    }


}
