import React, {FunctionComponent} from "react";
import {Divider, Grid, Link, Stack, Typography} from "@mui/material";
import Section from "../../../../components/Section";

import SummaryInfo from "./SummaryInfo";
import TableOfResults from "./TableOfResults";
import SubHeading from "../../../../components/Text/SubHeading";
import {Box} from "@mui/system";
import BodyText from "../../../../components/Text/BodyText";

interface Props {
  items: Array<Vulnerability>,
}

const SingleScan: FunctionComponent<Props> = (props) => {
  return (
    <Section border>
      <Grid container sx={{height: "100%"}}>
        <Grid item xs={4} sx={{borderRight: 1}}>
          <SummaryInfo/>
        </Grid>
        <Grid item xs={8}>
          <TableOfResults items={props.items}/>
        </Grid>
      </Grid>
    </Section>)
}

export default SingleScan;