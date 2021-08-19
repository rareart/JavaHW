package task2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {
    private final Connection connection;
    private final DataListener<String> messageListener;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
        this.messageListener = new MessageListener<>();
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        try {
            // prepare statement with sql
            PreparedStatement ps = connection.prepareStatement("select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                    "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                    " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
            // inject parameters to sql
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
            ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
            // execute query and get the results
            ResultSet results = ps.executeQuery();
            // create a StringBuilder holding a resulting html
            ResultingHtmlBuilder resultingHtmlBuilder = new ResultingHtmlBuilder(results);
            resultingHtmlBuilder.buildString();
            // now when the report is built we need to send it to the recipients list
            messageListener.additionalData(recipients);
            messageListener.onEvent(resultingHtmlBuilder.getString());
        } catch (SQLException | DataListenerException e) {
            e.printStackTrace();
        }
    }
}
