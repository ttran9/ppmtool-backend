package tran.example.ppmtool.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tran.example.ppmtool.constants.mapping.MappingConstants.URLS_REGEX;
import static tran.example.ppmtool.constants.mapping.MappingConstants.PB_UPT_REGEX;

public class RedirectToIndexFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        if (matchRegexToRequest(URLS_REGEX, requestURI)) {
            request.getRequestDispatcher("/").forward(request, response);
        }
        else if(matchRegexToRequest(PB_UPT_REGEX, requestURI)) {
            request.getRequestDispatcher(requestURI).forward(request, response);
         }
         else {
            chain.doFilter(request, response);
        }
    }

    private boolean matchRegexToRequest(String regex, String requestUrl) {
        /*
         * helper method that checks if the url we are typing in matches either
         * a known URL such as projectBoard/projectIdentifier, login, register, etc.
         * if we return true from this method then we will forward to the home mapping, index.html which will allow
         * BrowserRouter to kick in and render the appropriate page.
         */
        Pattern phoneNumberPattern = Pattern.compile(regex);
        Matcher pnMatcher = phoneNumberPattern.matcher(requestUrl);
        return pnMatcher.find();
    }
}

