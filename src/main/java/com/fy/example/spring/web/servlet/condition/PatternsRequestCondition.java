package com.fy.example.spring.web.servlet.condition;

import com.fy.example.spring.utils.StringUtils;
import com.fy.example.spring.web.util.AntPathMatcher;
import com.fy.example.spring.web.util.PathMatcher;
import com.fy.example.spring.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 模式请求路径过滤器
 *
 * @author: ya.fang
 * @create: 2017-12-18
 **/
public final class PatternsRequestCondition extends AbstractRequestCondition<PatternsRequestCondition> {

    private final Set<String> patterns;

    private final UrlPathHelper pathHelper;

    private final PathMatcher pathMatcher;

    private final boolean useSuffixPatternMatch;

    private final boolean useTrailingSlashMatch;

    private final List<String> fileExtensions = new ArrayList<String>();


    /**
     * Creates a new instance with the given URL patterns.
     * Each pattern that is not empty and does not start with "/" is prepended with "/".
     * @param patterns 0 or more URL patterns; if 0 the condition will match to every request.
     */
    public PatternsRequestCondition(String... patterns) {
        this(asList(patterns), null, null, true, true, null);
    }

    /**
     * Additional constructor with flags for using suffix pattern (.*) and
     * trailing slash matches.
     * @param patterns the URL patterns to use; if 0, the condition will match to every request.
     * @param urlPathHelper for determining the lookup path of a request
     * @param pathMatcher for path matching with patterns
     * @param useSuffixPatternMatch whether to enable matching by suffix (".*")
     * @param useTrailingSlashMatch whether to match irrespective of a trailing slash
     */
    public PatternsRequestCondition(String[] patterns, UrlPathHelper urlPathHelper, PathMatcher pathMatcher,
                                    boolean useSuffixPatternMatch, boolean useTrailingSlashMatch) {

        this(asList(patterns), urlPathHelper, pathMatcher, useSuffixPatternMatch, useTrailingSlashMatch, null);
    }


    public PatternsRequestCondition(String[] patterns, UrlPathHelper urlPathHelper,
                                    PathMatcher pathMatcher, boolean useSuffixPatternMatch, boolean useTrailingSlashMatch,
                                    List<String> fileExtensions) {

        this(asList(patterns), urlPathHelper, pathMatcher, useSuffixPatternMatch, useTrailingSlashMatch, fileExtensions);
    }

    /**
     * Private constructor accepting a collection of patterns.
     */
    private PatternsRequestCondition(Collection<String> patterns, UrlPathHelper urlPathHelper,
                                     PathMatcher pathMatcher, boolean useSuffixPatternMatch, boolean useTrailingSlashMatch,
                                     List<String> fileExtensions) {

        this.patterns = Collections.unmodifiableSet(prependLeadingSlash(patterns));
        this.pathHelper = (urlPathHelper != null ? urlPathHelper : new UrlPathHelper());
        this.pathMatcher = (pathMatcher != null ? pathMatcher : new AntPathMatcher());
        this.useSuffixPatternMatch = useSuffixPatternMatch;
        this.useTrailingSlashMatch = useTrailingSlashMatch;
        if (fileExtensions != null) {
            for (String fileExtension : fileExtensions) {
                if (fileExtension.charAt(0) != '.') {
                    fileExtension = "." + fileExtension;
                }
                this.fileExtensions.add(fileExtension);
            }
        }
    }

    private static List<String> asList(String... patterns) {
        return (patterns != null ? Arrays.asList(patterns) : Collections.<String>emptyList());
    }

    private static Set<String> prependLeadingSlash(Collection<String> patterns) {
        if (patterns == null) {
            return Collections.emptySet();
        }
        Set<String> result = new LinkedHashSet<String>(patterns.size());
        for (String pattern : patterns) {
            if (StringUtils.hasLength(pattern) && !pattern.startsWith("/")) {
                pattern = "/" + pattern;
            }
            result.add(pattern);
        }
        return result;
    }


    @Override
    public PatternsRequestCondition combine(PatternsRequestCondition other) {
        return null;
    }

    @Override
    public PatternsRequestCondition getMatchingCondition(HttpServletRequest request) {
        if (this.patterns.isEmpty()) {
            return this;
        }

        String lookupPath = this.pathHelper.getLookupPathForRequest(request);
        List<String> matches = getMatchingPatterns(lookupPath);

        return matches.isEmpty() ? null :
                new PatternsRequestCondition(matches, this.pathHelper, this.pathMatcher, this.useSuffixPatternMatch,
                        this.useTrailingSlashMatch, this.fileExtensions);
    }

    public List<String> getMatchingPatterns(String lookupPath) {
        List<String> matches = new ArrayList<String>();
        for (String pattern : this.patterns) {
            String match = getMatchingPattern(pattern, lookupPath);
            if (match != null) {
                matches.add(match);
            }
        }
        Collections.sort(matches, this.pathMatcher.getPatternComparator(lookupPath));
        return matches;
    }

    private String getMatchingPattern(String pattern, String lookupPath) {
        if (pattern.equals(lookupPath)) {
            return pattern;
        }
        if (this.useSuffixPatternMatch) {
            if (!this.fileExtensions.isEmpty() && lookupPath.indexOf('.') != -1) {
                for (String extension : this.fileExtensions) {
                    if (this.pathMatcher.match(pattern + extension, lookupPath)) {
                        return pattern + extension;
                    }
                }
            }
            else {
                boolean hasSuffix = pattern.indexOf('.') != -1;
                if (!hasSuffix && this.pathMatcher.match(pattern + ".*", lookupPath)) {
                    return pattern + ".*";
                }
            }
        }
        if (this.pathMatcher.match(pattern, lookupPath)) {
            return pattern;
        }
        if (this.useTrailingSlashMatch) {
            if (!pattern.endsWith("/") && this.pathMatcher.match(pattern + "/", lookupPath)) {
                return pattern +"/";
            }
        }
        return null;
    }

    @Override
    public int compareTo(PatternsRequestCondition other, HttpServletRequest request) {
        return 0;
    }

    @Override
    protected Collection<?> getContent() {
        return null;
    }

    @Override
    protected String getToStringInfix() {
        return null;
    }
}
