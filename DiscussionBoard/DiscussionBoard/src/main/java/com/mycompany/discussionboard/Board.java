/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discussionboard;

import java.util.Date;

/**
 *
 * @author Stephen
 */
public class Board {
    String name;
    String content;
    String date;
    Board(){
        
    }
    Board(String name, String content,String date){
        this.name = name;
        this.content = content;
        this.date = date;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
