import React, {FunctionComponent} from 'react';
import {Box, Typography, Grid, Button} from "@mui/material";
import COLOURS from "../../constants/colours";

interface Props {

}

const LandingTopPanel: FunctionComponent<Props> = (props) => {

  return (
    <div style={{
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      left: '50%'
    }}>
      <Grid container spacing={2}>
        <Grid item xs={4}>
          <Box
            component="img"
            sx={{
              height: 350,
              width: 350,
            }}
            src="/assets/griffin-logo.png"
          />
        </Grid>
        <Grid item xs={2}/>
        <Grid item xs={6}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
              }}>
                <Typography style={{fontWeight: 600}} variant={"h5"}>
                  Griffin, the cooler Grafeas
                </Typography>
              </div>
            </Grid>
            <Grid item style={{height: '100%'}} xs={12}>
              <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
              }}>
                <Button variant="contained">
                  Login
                </Button>
              </div>
            </Grid>
            <Grid item xs={12}>
              <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
              }}>
                <Typography variant={"h6"}>
                  Or
                </Typography>
              </div>
            </Grid>
            <Grid item xs={12}>
              <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
              }}>
                <Button variant="contained">
                  Register
                </Button>
              </div>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </div>
  );
};

export default LandingTopPanel;