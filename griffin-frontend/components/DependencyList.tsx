import React, { FunctionComponent } from 'react';
import {List, ListItem, ListItemButton, ListItemText} from "@mui/material";
import {Box} from "@mui/system";

interface Props {
    items: [{name: string}]
}

const DependencyList: FunctionComponent<Props> = (props) => {

    const renderItems = () => {
        return props.items.map( item => {
            return (
                <ListItem>
                    <ListItemButton>
                        <ListItemText primary={item.name} />
                    </ListItemButton>
                </ListItem>);
        });
    };

  return (
      <Box>
          <List>
              {renderItems()}
          </List>
      </Box>

  );
};

export default DependencyList;
