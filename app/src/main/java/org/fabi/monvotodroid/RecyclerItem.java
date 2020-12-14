package org.fabi.monvotodroid;

import java.util.List;

public class RecyclerItem {
    private int mImageResource;
    private String mText;

public RecyclerItem(int ImageResource, String Text)
{
    mImageResource = ImageResource;
    mText = Text;
}
public int getImageResource()
{
    return mImageResource;
}
    public String getText()
    {
        return mText;
    }
}