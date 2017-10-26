package com.tanx.expirit.bulk;

/*
 *
 * Copyright 2015 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

/**
 * 
 * {@link BulkOperation} contains all details of a RESTful operation.
 *
 */
@Data
public final class BulkOperation {

  private String url;
  private String method = "GET";
  private Object body;
  private Map<String, String> headers = new LinkedHashMap<String, String>();
  private boolean silent = false;

  /**
   * Returns the URL of this RESTful operation.
   * 
   * @return a URL string
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets the URL of this RESTful operation.
   * 
   * @param url
   *          a URL string
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Returns the HTTP method of this RESTful operation.
   * 
   * @return a HTTP method string
   */
  public String getMethod() {
    return method;
  }

  /**
   * Sets the HTTP method of this RESTful operation.
   * 
   * @param method
   *          a HTTP method string
   */
  public void setMethod(String method) {
    this.method = method;
  }


  /**
   * Returns the HTTP headers of this RESTful operation.
   * 
   * @return headers of a RESTful operation
   */
  public Map<String, String> getHeaders() {
    return headers;
  }

  /**
   * Sets the HTTP headers of this RESTful operation.
   * 
   * @param headers
   *          headers of a RESTful operation
   */
  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  /**
   * Returns if this RESTful operation result should be omitted.
   * 
   * @return true if this RESTful operation is silent, false otherwise
   */
  public boolean isSilent() {
    return silent;
  }

  /**
   * Sets if this RESTful operation result should be omitted.
   * 
   * @param silent
   *          true if this RESTful operation is silent, false otherwise
   */
  public void setSilent(boolean silent) {
    this.silent = silent;
  }

}