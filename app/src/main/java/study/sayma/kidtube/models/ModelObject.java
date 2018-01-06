package study.sayma.kidtube.models;

import study.sayma.kidtube.R;

public enum ModelObject {

    RHYMES(R.string.tvFragRhymes, R.layout.activity_item_view),
    MATHS(R.string.tvFragMath, R.layout.activity_item_view),
    STORIES(R.string.tvFragStories, R.layout.activity_item_view),
    ALPHABETS(R.string.tvFragAlphabets, R.layout.activity_playlist_view),
    CARTOONS(R.string.tvFragCartoons, R.layout.activity_item_view),
    SCIENCE(R.string.tvFragScience, R.layout.activity_item_view);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
