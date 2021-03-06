/**
 * Copyright (c) 2013-2014, jcabi.com
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

import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.util.Map;
import javax.validation.constraints.NotNull;

/**
 * Github pull requests.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.3
 * @see <a href="http://developer.github.com/v3/pulls/">Pull Request API</a>
 */
@Immutable
public interface Pulls {

    /**
     * Owner of them.
     * @return Repo
     */
    @NotNull(message = "repository is never NULL")
    Repo repo();

    /**
     * Get specific get by number.
     * @param number Pull request number
     * @return Pull request
     * @see <a href="http://developer.github.com/v3/pulls/#get-a-single-pull-request">Get a Single Pull Request</a>
     */
    @NotNull(message = "pull is never NULL")
    Pull get(int number);

    /**
     * Create new get.
     * @param title Title
     * @param head Head
     * @param base Base
     * @return Issue just created
     * @throws IOException If there is any I/O problem
     * @see <a href="http://developer.github.com/v3/pulls/#create-a-pull-request">Create a Pull Request</a>
     */
    @NotNull(message = "pull is never NULL")
    Pull create(
        @NotNull(message = "pull title is never NULL") String title,
        @NotNull(message = "head is never NULL") String head,
        @NotNull(message = "base is never NULL") String base)
        throws IOException;

    /**
     * Iterate them all.
     * @param params Params
     * @return Iterator of issues
     * @see <a href="http://developer.github.com/v3/pulls/#list-pull-requests">List Pull Requests</a>
     */
    @NotNull(message = "iterable is never NULL")
    Iterable<Pull> iterate(@NotNull(message = "map of params can't be NULL")
        Map<String, String> params);

}
