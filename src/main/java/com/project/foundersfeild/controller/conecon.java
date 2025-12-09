package com.project.foundersfeild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.foundersfeild.model.connections;
import com.project.foundersfeild.service.conser;

@RestController
@RequestMapping("/connections")
@CrossOrigin(origins = "http://localhost:5500") // adjust if frontend runs elsewhere
public class conecon {

    @Autowired
    private conser ser;

    @PostMapping("/send")
    public connections sendreq(@RequestParam Long reqid, @RequestParam Long recid) {
        return ser.sendreq(reqid, recid);
    }

    @PostMapping("/{id}/accept")
    public connections accreq(@PathVariable Long id) {
        return ser.accreq(id);
    }

    @PostMapping("/{id}/reject")
    public connections rejreq(@PathVariable Long id) {
        return ser.rejreq(id);
    }

    @GetMapping("/{id}")
    public List<connections> getcon(@PathVariable Long id) {
        return ser.getcon(id);
    }

    @GetMapping("/check")
    public String checkConnection(@RequestParam Long user1, @RequestParam Long user2) {
        return ser.checkConnectionStatus(user1, user2);
    }
}
