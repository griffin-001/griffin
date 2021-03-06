import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Box} from "@mui/system";
import NavBar from "./NavBar/NavBar";
import CenteredBox from "./CenteredBox";

interface Props {
  // toggles if the page containers contents are centered or not
  centered?: boolean;

  // hides the nav bar
  disableNav?: boolean;
}

const PageContainer: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Box sx={{
      width: "100%",
      height: "100vh",
    }}>

      {!props.disableNav && <NavBar/>}

      {(props.centered) ? (
        <CenteredBox>
          {props.children}
        </CenteredBox>
      ) : (
        <React.Fragment>
          {/*This is an offset for the nav bar*/}
          <Box sx={{height: "64px"}}/>
          {props.children}
        </React.Fragment>
      )
      }

    </Box>
  );
};

export default PageContainer;