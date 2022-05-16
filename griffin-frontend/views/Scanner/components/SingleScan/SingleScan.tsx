import React, {FunctionComponent} from "react";
import {Divider, Grid, Link, Stack, Typography} from "@mui/material";
import Section from "../../../../components/Section";

import SummaryInfo from "./SummaryInfo";
import TableOfResults from "./TableOfResults";

interface Props {
  scan: Scan
}

const SingleScan: FunctionComponent<Props> = (props) => {
  return (
    <Section border>
      <Grid container sx={{height: "100%"}}>
        <Grid item xs={4} sx={{borderRight: 1}}>
          <SummaryInfo
            dateTime={props.scan.dateTime}
            summary={props.scan.summary}
          />
        </Grid>
        <Grid item xs={8}>
          <TableOfResults data={props.scan.data}/>
        </Grid>
      </Grid>
    </Section>)
}

export default SingleScan;