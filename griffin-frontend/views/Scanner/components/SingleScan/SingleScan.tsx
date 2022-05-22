import React, {FunctionComponent, useEffect} from "react";
import {Divider, Grid, Link, Stack, Typography} from "@mui/material";
import Section from "../../../../components/Section";

import SummaryInfo from "./SummaryInfo";
import TableOfResults from "./TableOfResults";

interface Props {
  scan: Scan
}

const SingleScan: FunctionComponent<Props> = (props) => {

  useEffect(() => {
    console.log(props.scan.summary)
  }, [props]);

  return (
    <Section border>
      <Grid container sx={{height: "100%"}}>
        <Grid item xs={4} sx={{borderRight: 1}}>
          <SummaryInfo
            date={props.scan.date}
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