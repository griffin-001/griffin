import React, {FunctionComponent} from 'react';
import PageContainer from "../../components/PageContainer";
import {Box, Button, Grid, Typography} from "@mui/material";
import LeftLandingPanel from "./components/LeftLandingPanel";
import RightLandingPanel from "./components/RightLandingPanel";

interface Props {

}

// todo NOTE: All local components / hooks / utils etc.
//  can just go within the landing folder in views
//  Only the global stuff needs to go in a separate folder

class Landing extends React.Component<Props> {
  render() {

    return (
      <PageContainer isCentered disableNav>
        <Grid
          container
          spacing={2}
          sx={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          <LeftLandingPanel/>
          <RightLandingPanel/>
        </Grid>
      </PageContainer>
    );
  }
}

export default Landing;