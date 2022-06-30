package com.mindex.challenge.data;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure(Employee employee, int numberOfReports){
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public ReportingStructure(Employee employee){
        this.employee = employee;
        this.numberOfReports = calculateNumberOfReports(this.employee);
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return this.numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    private int calculateNumberOfReports(Employee employee) {
        //All employees start with 0 direct reports
        int totalNumberOfReports = 0;
        if(employee.getDirectReports() != null){
            //Loop through direct reports and increment total reports by each direct report
            //Recursively calculate direct reports of each direct report to return indirect reports
            for(Employee directReport: employee.getDirectReports()){
                totalNumberOfReports++;
                totalNumberOfReports += calculateNumberOfReports(directReport);
            }
        }

        return totalNumberOfReports;
    }

}
