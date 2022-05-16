import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Box} from "@mui/material";

interface Props {
  border?: boolean; // add a border
  marginBottom?: boolean; // add margin to the bottom
}

const Section: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <React.Fragment>
      <Box sx={{
        mt: 3,
        mx: 5,
        border: !!props.border ? 1 : "",
      }}>
        {props.children}
      </Box>
      <Box
        sx={{
          height: !!props.marginBottom ? 30 : 0,
        }}
      />
    </React.Fragment>


  );
};

export default Section;