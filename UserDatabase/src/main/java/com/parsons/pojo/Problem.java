package com.parsons.pojo;

import org.json.JSONObject;

public class Problem {

    private String scenario = "";
    private String task = "";
    private String data = "";
    private String pythonCode = ""; // Stores the generated Python code
    private JSONObject correctOutput = new JSONObject(); // Stores the correct output

    // Getter and Setter for scenario
    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    // Getter and Setter for task
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    // Getter and Setter for data
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // Getter and Setter for pythonCode
    public String getPythonCode() {
        return pythonCode;
    }

    public void setPythonCode(String pythonCode) {
        this.pythonCode = pythonCode;
    }

    // Getter and Setter for correctOutput
    public JSONObject getCorrectOutput() {
        return correctOutput;
    }

    public void setCorrectOutput(JSONObject correctOutput) {
        this.correctOutput = correctOutput;
    }
}
