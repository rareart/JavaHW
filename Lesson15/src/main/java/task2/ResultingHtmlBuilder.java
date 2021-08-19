package task2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultingHtmlBuilder {
    private final ResultSet results;
    private String resultString;

    public ResultingHtmlBuilder(ResultSet results) {
        this.results = results;
    }

    public void buildString() throws SQLException {
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        double totals = 0;
        while (results.next()) {
            // process each row of query results
            resultingHtml.append("<tr>"); // add row start tag
            resultingHtml.append("<td>").append(results.getString("emp_name")).append("</td>"); // appending employee name
            resultingHtml.append("<td>").append(results.getDouble("salary")).append("</td>"); // appending employee salary for period
            resultingHtml.append("</tr>"); // add row end tag
            totals += results.getDouble("salary"); // add salary to totals
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");
        resultString = resultingHtml.toString();
    }

    public String getString(){
        return resultString;
    }
}
