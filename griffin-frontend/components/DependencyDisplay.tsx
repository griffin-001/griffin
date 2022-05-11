import React, { FunctionComponent } from 'react';
import {Box} from "@mui/system";
import {CardContent, Typography} from "@mui/material";

interface OwnProps {
  item: {name: string, version: string, description: string}
}

type Props = OwnProps;

const DependencyDisplay: FunctionComponent<Props> = (props) => {

  return (
      <Box>
        <CardContent>
          <Typography variant="h5" component="div">
            {props.item.name}
          </Typography>
          <Typography sx={{ mb: 1.5 }}>
            Version: {props.item.version}
          </Typography>
          <Typography variant="body2">
            {props.item.description}
          </Typography>
        </CardContent>
      </Box>);
};

export default DependencyDisplay;
