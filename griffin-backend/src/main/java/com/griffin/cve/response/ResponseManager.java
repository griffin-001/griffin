package com.griffin.cve.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.griffin.cve.Vulnerability;
import com.griffin.insightsdb.model.RepositorySnapShot;

public final class ResponseManager {
    private static ResponseManager instance = null;
    // scanHistory should have the latest scan at the head of the list
    private List<ScanResponse> scanHistory = new ArrayList<>();

    private ResponseManager() {}

    public static ResponseManager getInstance() {
        if (instance == null) {
            instance = new ResponseManager();
        }
        return instance;
    }

    /**
     * Handles creating a Response Object for API request. Stores the newly created response for state
     * preservation.
     * @param results 
     * @return ScanResponse object
     */
    public ScanResponse mapToResponse(HashMap<RepositorySnapShot, List<Vulnerability>> results) {
        ScanResponse response = new ScanResponse(results);

        scanHistory.add(0, response);
        return response;
    }

    public List<ScanResponse> getScanHistory() {
        return this.scanHistory;
    }
}
