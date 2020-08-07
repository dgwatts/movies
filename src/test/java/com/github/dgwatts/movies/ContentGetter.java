package com.github.dgwatts.movies;

import java.io.StringReader;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

class ContentGetter implements ResultHandler {

	private String content;
	private JsonElement root;

	@Override
	public void handle(MvcResult mvcResult) throws Exception {
		content = mvcResult.getResponse().getContentAsString();
		root = JsonParser.parseReader(new StringReader(content));
	}

	public String getContent() {
		return content;
	}

	public JsonElement getRoot() {
		return root;
	}
}
