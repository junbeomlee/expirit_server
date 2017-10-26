package com.tanx.expirit.bulk;

/*
*
* Copyright 2016 Wei-Ming Wu
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

import javax.servlet.http.HttpServletRequest;

/**
* 
* {@link BulkApiService} defines the service interface for
* {@link BulkApiController} to use.
*
*/
public interface BulkService {

 /**
  * Processes the given {@link BulkRequest} and then returns a
  * {@link BulkResponse} as result.
  * 
  * @param req
  *          a {@link BulkRequest}
  * @param servReq
  *          a {@link HttpServletRequest}
  * @return a {@link BulkResponse}
  */
 public BulkResponse bulk(BulkRequest req, HttpServletRequest servReq);

}
