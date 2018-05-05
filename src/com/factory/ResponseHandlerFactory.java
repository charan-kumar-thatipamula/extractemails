package com.factory;

import com.util.ResponseHandler;
import com.util.journalsplos.JsonResponseHandlerJP;

public class ResponseHandlerFactory {
	public ResponseHandler getResponseHandler(String url) {
		if (url.indexOf("journals.plos.org") != -1) {
			return new JsonResponseHandlerJP();
		}
		return null;
	}
}
