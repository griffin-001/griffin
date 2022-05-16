import React, {FunctionComponent} from "react";
import {Box} from "@mui/system";
import {Divider, Grid, Typography} from "@mui/material";
import Section from "../../../components/Section";
import SubHeading from "../../../components/Text/SubHeading";
import BodyText from "../../../components/Text/BodyText";

interface Props {
  items: Array<Vulnerability>,
}

const ReportSummary: FunctionComponent<Props> = (props) => {
  return (
    <Section border>
      <Box sx={{m: 2}}>
        <Grid container spacing={2}>
          <Grid item xs={8}>
            <SubHeading> Summary </SubHeading>
          </Grid>
          <Grid item xs={4}>
            <Box display="flex" justifyContent="flex-end" sx={{m: 1}}>
              <SubHeading> Last Scan: Date </SubHeading>
            </Box>
          </Grid>
        </Grid>
      </Box>
      <Box sx={{m: 2}}>
        <Grid container spacing={2}>
          <Grid item xs={3}>
            <BodyText bold>Total Vulnerabilities</BodyText>
            <Divider/>
            <Box sx={{mt: 1}}>
              <BodyText>Number of
                Vulnerabilities: {props.items.length}</BodyText>
            </Box>
          </Grid>
          <Grid item xs={1.5}/>
          <Grid item xs={3}>
            <BodyText bold>Projects</BodyText>
            <Divider/>
            <Box sx={{mt: 1}}>
              <BodyText>Project Affected</BodyText>
              <BodyText>Project Passed</BodyText>
            </Box>
          </Grid>
          <Grid item xs={1.5}/>
          <Grid item xs={3}>
            <BodyText bold>Risk Summary</BodyText>
            <Divider/>
            <Box sx={{mt: 1}}>
              <BodyText>Number of High Risk</BodyText>
              <BodyText>Number of Medium Risk</BodyText>
              <BodyText>Number of Low Risk</BodyText>
            </Box>
          </Grid>
          <Grid item xs={1}/>
        </Grid>
      </Box>
    </Section>)
}

export default ReportSummary;