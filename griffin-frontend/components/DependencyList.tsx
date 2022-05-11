import React, { FunctionComponent, useState} from 'react';
import {List, ListItem, ListItemButton, ListItemText} from "@mui/material";
import {Box} from "@mui/system";

interface Props {
    items: Array<{name: string}>,
    onSelect: (item: {name: string}) => void
}

const DependencyList: FunctionComponent<Props> = (props) => {

    const [selectedIndex, setSelectedIndex] = useState(1);

    const handleListItemClick = (
        event: React.MouseEvent<HTMLDivElement, MouseEvent>,
        index: number,
        item: {name: string}
    ) => {
        setSelectedIndex(index);
        props.onSelect(item);
    };

    const renderItems = () => {
        return props.items.map( (item, index) => {
            return (
                <ListItem>
                    <ListItemButton
                        selected={selectedIndex === index}
                        onClick={(event) =>
                            handleListItemClick(event, index, item)}>
                        <ListItemText primary={item.name}/>
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
