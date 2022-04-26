package cl.com.fidelis.controller;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import cl.com.fidelis.exception.ExceptionCustom;
import cl.com.fidelis.model.Documento;

@Controller
@RequestMapping(value = "/form")
public class CoberturaController {

	JSONObject jsonObject = null;

	@Autowired
	RestTemplate restTemplate;

	@Value("${urlToken}")
	private String urlToken;

	@Value("${urlValid}")
	private String urlValid;

	@Value("${credentials}")
	private String credentials;

	@Value("${usuario}")
	private String usuario;

	@Value("${clave}")
	private String clave;

	public static final String FORM_VIEW = "form";
	public static final String RESULT_VIEW = "resultado";

	@GetMapping(value = "/verform")
	public String showForm(Model model) {
		model.addAttribute("documento", new Documento());
		return FORM_VIEW;
	}

	@PostMapping(value = "/getapi")
	public Object getApi(@ModelAttribute("documento") Documento documento) {
		String result = "";
		ResponseEntity<String> response = null;
		ModelAndView mav = new ModelAndView(RESULT_VIEW);

		try {

			String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Basic " + encodedCredentials);

			JSONObject bodyRequest = new JSONObject();
			bodyRequest.put("usuario", usuario);
			bodyRequest.put("clave", clave);
			HttpEntity<String> request = new HttpEntity<String>(bodyRequest.toString(), headers);

			String access_token_url = urlToken;

			response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

			JSONObject json = new JSONObject(response.getBody());
			String access_token = json.getJSONObject("result").getString("access_token");

			HttpHeaders headers1 = new HttpHeaders();
			headers1.add("Authorization", "Bearer " + access_token);

			JSONObject bodyRequest1 = new JSONObject();
			bodyRequest1.put("rut", String.valueOf(documento.getRut()));

			HttpEntity<String> entity = new HttpEntity<>(bodyRequest1.toString(), headers1);

			ResponseEntity<String> responseurlValid = restTemplate.exchange(urlValid, HttpMethod.POST, entity,
					String.class);

			JSONObject responseAfiliado = new JSONObject(responseurlValid.getBody());
			JSONObject resultJson = responseAfiliado.getJSONObject("result");

			mav.addObject("resp", resultJson.toString());

		} catch (HttpClientErrorException e) {
			result = new ExceptionCustom().getDescription(e.getStatusCode());
			mav.addObject("resp", result);
		}

		return mav;
	}

}