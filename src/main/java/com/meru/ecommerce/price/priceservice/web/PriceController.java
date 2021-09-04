package com.meru.ecommerce.price.priceservice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meru.ecommerce.price.priceservice.dao.PriceRepository;
import com.meru.ecommerce.price.priceservice.entity.Price;
import com.meru.ecommerce.price.priceservice.service.PriceService;

@Controller
public class PriceController {
    static String WELCOME_MSG = "Welcome to Meru Price Service. This is not a valid service path.";
    static String CREATE_SUB_COMPONENT_MSG = "Create %s for Price %d in Price is %s";
    static String UPDATE_SUB_COMPONENT_MSG = "Update %s for Price %d in Price is %s";
    static String DELETE_MSG = "Delete Price for PriceId: %s %s";
    static String SUCCESS = "Success";
    static String ERROR = "Failed";
    static String COMPONENT_PRICE = "Price";
    static String RETURN_TEMPLATE = "{\"message\":\"%s\"}";

    @Autowired
    PriceService ps;
    
    @Autowired
	PriceRepository pr;
    
    
    @RequestMapping(value={"/index"},method = RequestMethod.GET)
	public String index(Model model){
		List<Price> pricingDetails= ps.getAllProductsPrice();
		model.addAttribute("pricingDetails", pricingDetails);
		return "index";
	}
	
	@RequestMapping(value="search",method = RequestMethod.GET)
	public String search(){
		return "searchPricing";
	}
	
	
	@RequestMapping(value="searchbyid",method = RequestMethod.GET)
	public String searchById(@RequestParam(value = "search", required = false) int id, Model model){		
		Price pricing=  ps.getPriceById(id);	 
		 model.addAttribute("search", pricing);	 
		return "searchPricing";
	
	}
	
	@RequestMapping(value="add")
	public String addPricing(Model model) {
		
		model.addAttribute("pricing", new Price());
		return "addPricing";
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public String savePricing(Price pricing) {
		pr.save(pricing);
		return "redirect:/index";
	}
	
	@RequestMapping(value="/delete/{priceId}", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable("priceId") int priceId, Model model) {
		ps.removePrice(priceId);
		return "redirect:/index";
	}

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<String> showInfo() {
        return ResponseEntity.badRequest().headers(new HttpHeaders()).body(WELCOME_MSG);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable int id) {
        Price price = ps.getPriceById(id);
        HttpStatus status = HttpStatus.OK;
        if (null == price) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(price, new HttpHeaders(), status);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<Price>> getAllProductsPrice() {
        return ResponseEntity.ok().body(ps.getAllProductsPrice());
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<String> deletePrice(@PathVariable int id) {
        boolean deleted = ps.removePrice(id);
        HttpStatus status = HttpStatus.OK;
        String msg = String.format(DELETE_MSG, id, SUCCESS);
        if (!deleted) {
            msg = String.format(DELETE_MSG, id, ERROR);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(String.format(RETURN_TEMPLATE, msg), new HttpHeaders(), status);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<String> addPrice(@RequestBody Price price) {
        boolean created = ps.createOrUpdatePrice(price);
        HttpStatus status = HttpStatus.OK;
        String msg = String.format(CREATE_SUB_COMPONENT_MSG, COMPONENT_PRICE, price.getPriceId(), SUCCESS);
        if (!created) {
            String.format(CREATE_SUB_COMPONENT_MSG, COMPONENT_PRICE, price.getPriceId(), ERROR);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(String.format(RETURN_TEMPLATE, msg), new HttpHeaders(), status);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<String> updatePrice(@PathVariable int id, @RequestBody Price price) {
        boolean updated = ps.createOrUpdatePrice(price);
        HttpStatus status = HttpStatus.OK;
        String msg = String.format(UPDATE_SUB_COMPONENT_MSG, COMPONENT_PRICE, id, SUCCESS);
        if (!updated) {
            msg = String.format(UPDATE_SUB_COMPONENT_MSG, COMPONENT_PRICE, id, ERROR);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(String.format(RETURN_TEMPLATE, msg), new HttpHeaders(), status);
    }
}
