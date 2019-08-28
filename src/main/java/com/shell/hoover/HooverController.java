package com.shell.hoover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shell/v1/hoover")
public class HooverController {
	
	@Autowired
	private HooverService hooverService;
	

	@PostMapping("/clean")
    public Hoover cleanTheRoom(@RequestBody HooverService hooverService) {
    	hooverService.setUp();
    	hooverService.cleanRoom();
        return hooverService.getCleaningDetails();
    }
	
    @GetMapping("/position")
    public Hoover hooverPosition() {
        return hooverService.getHooverPosition();
    }
    
    @GetMapping("/patchesCleaned")
    public Hoover noPatchesCleaned() {
        return hooverService.getNoOfPatchesCleaned();
    }
}
