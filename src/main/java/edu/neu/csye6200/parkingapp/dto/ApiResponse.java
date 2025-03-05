package edu.neu.csye6200.parkingapp.dto;


public class ApiResponse<T> {
    private boolean success; // Indicates if the operation was successful
    private T data;          // The actual data/dto
    private String error;    // Error message in case of failure

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.error = null; // No error in success
    }

    public ApiResponse(boolean success, String error) {
        this.success = success;
        this.data = null; // No data in error
        this.error = error;
    }

    public ApiResponse(boolean b, String validationErrorsOccurred, Object o) {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

