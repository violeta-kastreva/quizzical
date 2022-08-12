package bg.softuni.quizzical.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.web.servlet.error.ErrorController;


import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Quizzical</title>\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\n" +
                "    <meta content=\"A new design system for developing with less effort.\" name=\"description\">\n" +
                "    <meta content=\"BootstrapBay\" name=\"author\">\n" +
                "\n" +
                "    <link rel=\"icon\" th:href=\"${favicon}\">\n" +
                "\n" +
                "    <link crossorigin=\"anonymous\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css\"\n" +
                "          integrity=\"sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx\" rel=\"stylesheet\">\n" +
                "    <script crossorigin=\"anonymous\"\n" +
                "            integrity=\"sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa\"\n" +
                "            src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js\"></script>\n" +
                "    <link href=\"/css/lazy.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"/css/demo.css\" rel=\"stylesheet\">\n" +
                "    <link crossorigin=\"anonymous\" href=\"https://use.fontawesome.com/releases/v5.6.0/css/all.css\"\n" +
                "          integrity=\"sha384-aOkxzJ5uQz7WBObEZcHvV5JvRW3TUc2rNPA7pe3AwnsUohiw1Vj2Rgx2KSOkF5+h\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body class=\"index\">\n" +
                "<header>\n" +
                "    <nav class=\"navbar navbar-expand-sm navbar-dark bg-primary\">\n" +
                "        <div class=\"container\">\n" +
                "            <a class=\"navbar-brand\" href=\"/\">Quizzical</a>\n" +
                "\n" +
                "            <button aria-controls=\"navbarNavDropdown-7\"\n" +
                "                    aria-expanded=\"false\"\n" +
                "                    aria-label=\"Toggle navigation\"\n" +
                "                    class=\"navbar-toggler collapsed\"\n" +
                "                    data-target=\"#navbarNavDropdown-7\"\n" +
                "                    data-toggle=\"collapse\"\n" +
                "                    type=\"button\">\n" +
                "                <span class=\"navbar-toggler-icon\"></span>\n" +
                "            </button>\n" +
                "            <div class=\"collapse navbar-collapse\" id=\"navbarNavDropdown-7\">\n" +
                "                <ul class=\"navbar-nav ml-auto mx-2\">\n" +
                "\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </nav>\n" +
                "</header>\n" +
                "<div class=\"page-hero bg-white text-primary\" id=\"banner\">\n" +
                "    <div class=\"bubbles d-none d-md-block\">\n" +
                "        <!-- \t\t\t\tbubbles -->\n" +
                "\n" +
                "        <div class=\"bubble bubble-2 bubble-bottom-right bg-danger rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-3 bubble-top-right bg-warning rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-5 bg-info rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-6 bg-warning rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-7 bubble-top-right bg-danger rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-8 bubble-top-right bg-danger rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-9 bg-warning rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-10 bg-warning rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-11 bg-info rotate-bubble\"></div>\n" +
                "        <div class=\"bubble bubble-12 bubble-top-right bg-success rotate-bubble\"></div>\n" +
                "\n" +
                "        <div class=\"bubble bubble-13 bubble-top-left bg-success rotate-bubble\"></div>\n" +
                "        <!--         circles -->\n" +
                "        <div class=\"circle circle-1 bg-light rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-2 bg-success rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-3 bg-danger rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-4 bg-info rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-5 bg-info rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-6 bg-info rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-7 bg-warning rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-8 bg-white rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-9 bg-warning rotate-circle\"></div>\n" +
                "        <div class=\"circle circle-10 bg-danger rotate-circle\"></div>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"row\">\n" +
                "            <div class=\"col-12 mb-5\">\n" +
                "                <div class=\"title text-center pt-5\">\n" +
                "                    <h1 class=\"display-2 text-primary\">Something went wrong :(</h1>\n" +
                "                    <h4 class=\"text-primary\">The programmer is working on it!</h4>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<footer class=\"footer-1 bg-light text-dark pt-2\">\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"d-flex flex-column flex-md-row justify-content-between align-items-center\">\n" +
                "            <div class=\"copyright text-center\">\n" +
                "                <p class=\"small mb-0\">© 2022, made with <span class=\"text-danger\"><i class=\"fas fa-heart\"></i></span> by\n" +
                "                    VKastreva</p>\n" +
                "            </div>\n" +
                "            <div class=\"links\">\n" +
                "                <ul class=\"footer-menu list-unstyled d-flex flex-row text-center text-md-left\">\n" +
                "\n" +
                "\n" +
                "                    <li><a href=\"/\">Home</a></li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</footer>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "\n" +
                "\n";
    }

    public String getErrorPath() {
        return "/error";
    }
}
