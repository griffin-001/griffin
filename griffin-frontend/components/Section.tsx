import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Box} from "@mui/material";

interface Props {
  border?: boolean; // add a border
}

const Section: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Box sx={{
      pt: 3,
      px: 5,
    }}>
      <Box sx={{
        border: !!props.border ? 1 : "",
      }}>
        {props.children}
      </Box>
    </Box>
  );
};

export default Section;