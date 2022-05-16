import React, {FunctionComponent, useState} from 'react';
import {Box} from "@mui/system";
import PageContainer from "../../components/PageContainer";
import {
  Button,
  Grid,
  Typography
} from "@mui/material";
import VulnerabilitiesSummary from "./components/ReportSummary";
import VulnerabilitiesListAll from "./components/ReportListAll";
import Section from "../../components/Section";
import Heading from "../../components/Text/Heading";
import ReportHeader from "./components/ReportHeader";
import ReportSummary from "./components/ReportSummary";
import ReportListAll from "./components/ReportListAll";

interface Props {
  vulnerabilities: Array<Vulnerability>,
}

// todo add summary info for entire ecosystem
// number of existing vulnerabilities
// number of new vulnerabilities

// potentially could also add a table with
// todo add some summary info for each projectDependencies
//  i.e. number of vulnerabilities
//
const VulnerabilitiesReport: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer>
      <ReportHeader/>
      <ReportSummary
        items={props.vulnerabilities}
      />
      <ReportListAll
        items={props.vulnerabilities}
      />
    </PageContainer>

  );
};

export default VulnerabilitiesReport;