import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Box} from "@mui/system";

interface Props {

}

const CenteredBox: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Box sx={{
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      width: "100%",
      height: "100%",
    }}>
      {props.children}
    </Box>
  );
};

export default CenteredBox;