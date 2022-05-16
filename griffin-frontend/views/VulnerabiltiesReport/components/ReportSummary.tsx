import React, {FunctionComponent} from "react";
import {Box} from "@mui/system";
import {Divider, Grid, Link, Stack, Typography} from "@mui/material";
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
          <Grid item xs={6}>
            <SubHeading> 10/05/22 11:00PM </SubHeading>
          </Grid>
          <Grid item xs={6}>
            <Box display="flex" justifyContent="flex-end">
              <BodyText> </BodyText>
            </Box>
          </Grid>
        </Grid>
      </Box>
      <Box sx={{m: 2}}>
        <Grid container spacing={2}>
          <Grid item xs={3}>
            <Stack>
              <BodyText bold>Vulnerabilities</BodyText>
              <Divider/>
              <Box sx={{mt: 1}}/>
              <BodyText>
                14 vulnerabilities have been found across the entire system.
              </BodyText>
              <Box sx={{mt: 1}}/>
              <BodyText>
                {"3"} {" vulnerabilities have been removed since the last scan. "}
              </BodyText>
            </Stack>
          </Grid>
          <Grid item xs={1.5}/>
          <Grid item xs={3}>
            <Stack>
              <BodyText bold>Impact</BodyText>
              <Divider/>
              <Box sx={{mt: 1}}/>
              <BodyText> 1 project is affected.</BodyText>
              {/*<BodyText> 3 projects are affected</BodyText>*/}
              <Box sx={{mt: 1}}/>
              <BodyText> 3 repositories are affected. </BodyText>
            </Stack>
          </Grid>
        </Grid>
      </Box>
    </Section>)
}

export default ReportSummary;