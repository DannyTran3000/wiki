package com.wiki.controllers;

import com.wiki.helpers.SlugHelper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SingleController extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    // Get request url
    String url = request.getRequestURL().toString();
    // Extract article pathname
    String articlePathname = SlugHelper.extractArticlePathname(url);

    System.out.println(articlePathname);
  }
}
