package ac.za.dirisa.sst.controller.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.za.dirisa.sst.model.Institution;
import ac.za.dirisa.sst.service.InstitutionService;

@RestController
@RequestMapping("api/institution")
public class InstitutionApiController {

	@Autowired
	InstitutionService institutionService;

	@GetMapping("/institutions")
	public List<Institution> getInstitutions() {
		List<Institution> institutions = institutionService.findInstitutions();
		return institutions;
	}
}
