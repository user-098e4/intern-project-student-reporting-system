package com.vaish.elastic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UIController {

    @Autowired
    private ElasticSearchQuery elasticSearchQuery;

    @GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
        model.addAttribute("listProductDocuments",elasticSearchQuery.searchAllDocuments());
        System.out.println(elasticSearchQuery.searchAllDocuments());
        return "index.html";
    }

    @PostMapping("/saveStudent")
    public String saveProduct(@ModelAttribute("product") Student student) throws IOException {
        elasticSearchQuery.createOrUpdateDocument(student);
        return "redirect:/";
    }

    @GetMapping("/updateStudent/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {

        Student student = elasticSearchQuery.getDocumentById(id);
        model.addAttribute("student", student);
        return "updateStudent";
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "newStudent";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") String id) throws IOException {

        this.elasticSearchQuery.deleteDocumentById(id);
        return "redirect:/";
    }
}
