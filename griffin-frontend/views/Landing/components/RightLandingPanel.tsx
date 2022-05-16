import React, {FunctionComponent} from 'react';
import {Button, Grid, Typography} from "@mui/material";
import {useRouter} from "next/router";

interface Props {

}

const RightLandingPanel: FunctionComponent<Props> = (props) => {

  const router = useRouter();

  return (
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
        <Grid item xs={12} sx={{height: '100%'}}>
          <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center'
          }}>
            <Button
              variant="contained"
              onClick={() => {
                router.push("/vulnerabilitiesReport")
                  .catch((e) => {
                    //error

                  })
              }}
            >
              Get Started
            </Button>
          </div>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default RightLandingPanel;