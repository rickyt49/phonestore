package com.axonactive.phonestore.exception;

import org.springframework.http.HttpStatus;

public class BusinessLogicException {
    private static final String OWNER_NOT_FOUND_MSG_KEY = "OwnerNotExisted";
    private static final String OWNER_NOT_FOUND_MSG = "Owner Not Found";

    private static final String STORE_NOT_FOUND_MSG_KEY = "StoreNotExisted";
    private static final String STORE_NOT_FOUND_MSG = "Store Not Found";

    private static final String EMPLOYEE_NOT_FOUND_MSG_KEY = "EmployeeNotExisted";
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee Not Found";

    private static final String SPECIFICATION_NOT_FOUND_MSG_KEY = "SpecificationNotExisted";
    private static final String SPECIFICATION_NOT_FOUND_MSG = "Specification Not Found";

    private static final String BILL_NOT_FOUND_MSG_KEY = "BillNotExisted";
    private static final String BILL_NOT_FOUND_MSG = "Bill Not Found";

    private static final String BILL_DETAIL_NOT_FOUND_MSG_KEY = "BillDetailNotExisted";
    private static final String BILL_DETAIL_NOT_FOUND_MSG = "Bill Detail Not Found";

    private static final String CUSTOMER_NOT_FOUND_MSG_KEY = "CustomerNotExisted";
    private static final String CUSTOMER_NOT_FOUND_MSG = "Customer Not Found";

    private static final String SUPPLIER_NOT_FOUND_MSG_KEY = "SupplierNotExisted";
    private static final String SUPPLIER_NOT_FOUND_MSG = "Supplier Not Found";

    private static final String PHONE_NOT_FOUND_MSG_KEY = "PhoneNotExisted";
    private static final String PHONE_NOT_FOUND_MSG = "Phone Not Found";


    public static ResponseException notFound(String messageKey, String message) {
        return new ResponseException(messageKey, message, HttpStatus.NOT_FOUND);
    }

    public static ResponseException badRequest(String messageKey, String message) {
        return new ResponseException(messageKey, message, HttpStatus.BAD_REQUEST);
    }

    public static ResponseException internalServerError(String messageKey, String message) {
        return new ResponseException(messageKey, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseException OwnerNotFound() {
        return notFound(OWNER_NOT_FOUND_MSG_KEY, OWNER_NOT_FOUND_MSG);
    }

    public static ResponseException StoreNotFound() {
        return notFound(STORE_NOT_FOUND_MSG_KEY, STORE_NOT_FOUND_MSG);
    }

    public static ResponseException EmployeeNotFound() {
        return notFound(EMPLOYEE_NOT_FOUND_MSG_KEY, EMPLOYEE_NOT_FOUND_MSG);
    }

    public static ResponseException SpecificationNotFound() {
        return notFound(SPECIFICATION_NOT_FOUND_MSG_KEY, SPECIFICATION_NOT_FOUND_MSG);
    }

    public static ResponseException BillNotFound() {
        return notFound(BILL_NOT_FOUND_MSG_KEY, BILL_NOT_FOUND_MSG);
    }

    public static ResponseException BillDetailNotFound() {
        return notFound(BILL_DETAIL_NOT_FOUND_MSG_KEY, BILL_DETAIL_NOT_FOUND_MSG);
    }

    public static ResponseException CustomerNotFound() {
        return notFound(CUSTOMER_NOT_FOUND_MSG_KEY, CUSTOMER_NOT_FOUND_MSG);
    }

    public static ResponseException SupplierNotFound() {
        return notFound(SUPPLIER_NOT_FOUND_MSG_KEY, SUPPLIER_NOT_FOUND_MSG);
    }

    public static ResponseException PhoneNotFound() {
        return notFound(PHONE_NOT_FOUND_MSG_KEY, PHONE_NOT_FOUND_MSG);
    }
}
