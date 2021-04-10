package com.decagon.fintrackapp.payload;

import com.decagon.fintrackapp.model.ECashType;
import com.decagon.fintrackapp.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToMany;
import java.util.List;

@Getter
@Setter
public class CategoryRequest {
    private String description;

    //do we need min and max
    private Long min;
    private Long max;

    private List<User> approvalList;

    private String categoryName;

}
