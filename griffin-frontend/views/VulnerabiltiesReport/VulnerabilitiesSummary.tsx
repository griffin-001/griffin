import React, {FunctionComponent} from "react";
import {Box} from "@mui/system";
import {Divider, Grid, Typography} from "@mui/material";

interface Props {
  items: Array<Vulnerability>,
}

const VulnerabilitiesSummary: FunctionComponent<Props> = (props) => {
  return (
    <Box sx={{mt: 2, mx: 5, border: 1}}>
      <Box sx={{m: 2}}>
        <Grid container spacing={2}>
          <Grid item xs={8}>
            <Typography variant="h4"> Summary </Typography>
          </Grid>
          <Grid item xs={4}>
            <Box display="flex" justifyContent="flex-end" sx={{m: 1}}>
              <Typography variant="h5"> Last Scan: Date </Typography>
            </Box>
          </Grid>
        </Grid>
      </Box>
      <Box sx={{m: 3}}>
        <Grid container spacing={2}>
          <Grid item xs={3}>
            <Typography>Total Vulnerabilities</Typography>
            <Divider/>
            <Box sx={{mt: 1}}>
              <Typography>Number of
                Vulnerabilities: {props.items.length}</Typography>
            </Box>
          </Grid>
          <Grid item xs={4}>
            <Typography>Projects</Typography>
            <Divider/>
            <Box sx={{mt: 1}}>
              <Typography>Project Affected</Typography>
              <Typography>Project Passed</Typography>
            </Box>
          </Grid>
          <Grid item xs={3}>
            <Typography>Risk Summary</Typography>
            <Divider/>
            <Box sx={{mt: 1}}>
              <Typography>Number of High Risk</Typography>
              <Typography>Number of Medium Risk</Typography>
              <Typography>Number of Low Risk</Typography>
            </Box>
          </Grid>
        </Grid>
      </Box>
    </Box>)
}

export default VulnerabilitiesSummary;