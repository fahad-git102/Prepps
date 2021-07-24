package com.fahaddev.prepps.models;

import java.io.Serializable;
import java.util.List;

public class CollegeNavigatorModel implements Serializable {
    public int id;
    public String name;
    public String state;
    public String size;
    public String campus_housing;
    public String population;
    public String undergrad_student;
    public String grad_ratio;
    public String tuition_in_state;
    public String tuition_out_state;
    public String tuition_fees;
    public Object program_offered;
    public String created_at;
    public String updated_at;
    public List<ProgramsModel> programs;
    public int current_page;
    public String first_page_url;
    public int from;
    public int last_page;
    public String last_page_url;
    public String next_page_url;
    public String path;
    public int per_page;
    public String prev_page_url;
    public int to;
    public int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCampus_housing() {
        return campus_housing;
    }

    public void setCampus_housing(String campus_housing) {
        this.campus_housing = campus_housing;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getUndergrad_student() {
        return undergrad_student;
    }

    public void setUndergrad_student(String undergrad_student) {
        this.undergrad_student = undergrad_student;
    }

    public String getGrad_ratio() {
        return grad_ratio;
    }

    public void setGrad_ratio(String grad_ratio) {
        this.grad_ratio = grad_ratio;
    }

    public String getTuition_in_state() {
        return tuition_in_state;
    }

    public void setTuition_in_state(String tuition_in_state) {
        this.tuition_in_state = tuition_in_state;
    }

    public String getTuition_out_state() {
        return tuition_out_state;
    }

    public void setTuition_out_state(String tuition_out_state) {
        this.tuition_out_state = tuition_out_state;
    }

    public String getTuition_fees() {
        return tuition_fees;
    }

    public void setTuition_fees(String tuition_fees) {
        this.tuition_fees = tuition_fees;
    }

    public Object getProgram_offered() {
        return program_offered;
    }

    public void setProgram_offered(Object program_offered) {
        this.program_offered = program_offered;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<ProgramsModel> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramsModel> programs) {
        this.programs = programs;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
