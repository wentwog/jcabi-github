/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.github;

import com.rexsl.test.Request;
import com.rexsl.test.mock.MkAnswer;
import com.rexsl.test.mock.MkContainer;
import com.rexsl.test.mock.MkGrizzlyContainer;
import com.rexsl.test.request.ApacheRequest;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.json.JsonObject;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link GhPagination}.
 *
 * @author Giang Le (giang@vn-smartsolutions.com)
 * @version $Id$
 */
public final class GhPaginationTest {
    /**
     * HTTP Status-Code 200: OK.
     */
    private static final int HTTP_OK = HttpURLConnection.HTTP_OK;

    /**
     * GhPagination can jump to next page of results.
     *
     * @throws Exception if there is any problem
     */
    @Test
    public void jumpNextPage() throws Exception {
        final MkContainer container = new MkGrizzlyContainer().next(
            new MkAnswer.Simple(
                HTTP_OK, "[{\"body\":\"hey Jeff\"}]"
            ).withHeader("Link", "</repos?page=3&per_page=100>; rel=\"next\"")
        ).next(new MkAnswer.Simple(HTTP_OK, "[{\"body\":\"hi\"}]")).start();
        final Request request = new ApacheRequest(container.home());
        final GhPagination<JsonObject> page = new GhPagination<JsonObject>(
            request, new GhPagination.Mapping<JsonObject>() {
                @Override
                public JsonObject map(final JsonObject object) {
                    return object;
                }
            }
        );
        final Iterator<JsonObject> iterator = page.iterator();
        MatcherAssert.assertThat(
            iterator.next().toString(),
            Matchers.containsString("Jeff")
        );
        MatcherAssert.assertThat(
            iterator.next().getString("body"),
            Matchers.equalTo("hi")
        );
        container.stop();
    }

    /**
     * GhPagination can throw if there is no more elements in pagination.
     *
     * @throws Exception if there is any problem
     */
    @Test(expected = NoSuchElementException.class)
    public void throwsIfNoMoreElement() throws Exception {
        final MkContainer container = new MkGrizzlyContainer().next(
            new MkAnswer.Simple(HTTP_OK, "[{\"body\":\"hi jeff\"}]")
        ).start();
        final Request request = new ApacheRequest(container.home());
        final GhPagination<JsonObject> page = new GhPagination<JsonObject>(
            request, new GhPagination.Mapping<JsonObject>() {
                @Override
                public JsonObject map(final JsonObject object) {
                    return object;
                }
            }
        );
        final Iterator<JsonObject> iterator = page.iterator();
        MatcherAssert.assertThat(
            iterator.next().toString(),
            Matchers.notNullValue()
        );
        MatcherAssert.assertThat(
            iterator.next(),
            Matchers.notNullValue()
        );
        container.stop();
    }
}
