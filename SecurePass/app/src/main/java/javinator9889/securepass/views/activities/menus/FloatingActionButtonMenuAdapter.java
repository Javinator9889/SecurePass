/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 03/11/2018 - SecurePass.
 */
package javinator9889.securepass.views.activities.menus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter;
import uk.co.markormesher.android_fab.SpeedDialMenuItem;

/**
 * Custom class for displaying a custom menu inside a FloatingActionButton.
 */
public class FloatingActionButtonMenuAdapter extends SpeedDialMenuAdapter {
    private SparseArray<SpeedDialMenuItem> mMenuItems;
    private Typeface mCustomTypeface;
    private boolean mIsCustomBackgroundColorEnabled;
    private int mCustomBackgroundColor;
    private boolean mEnableIconRotating;

    private FloatingActionButtonMenuAdapter(SparseArray<SpeedDialMenuItem> menuItems,
                                            @Nullable Typeface customTypeface,
                                            int customBackgroundColor,
                                            boolean enableIconRotating) {
        System.out.println(customBackgroundColor);
        mMenuItems = menuItems;
        mCustomTypeface = customTypeface;
        mEnableIconRotating = enableIconRotating;
        if (customBackgroundColor == Integer.MAX_VALUE)
            mIsCustomBackgroundColorEnabled = false;
        else {
            mIsCustomBackgroundColorEnabled = true;
            mCustomBackgroundColor = customBackgroundColor;
        }
    }

    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    @NotNull
    @Override
    public SpeedDialMenuItem getMenuItem(@NotNull Context context, int key) {
        return mMenuItems.get(key, null);
    }

    @Override
    public float fabRotationDegrees() {
        return mEnableIconRotating ? 135F : 0F;
    }

    @Override
    public void onPrepareItemLabel(@NotNull Context context,
                                   int position,
                                   @NotNull TextView label) {
        label.setTypeface(mCustomTypeface);
    }

    @Override
    public int getBackgroundColour(int position) {
        if (!mIsCustomBackgroundColorEnabled)
            return super.getBackgroundColour(position);
        else
            return mCustomBackgroundColor;
    }

    public static Builder Builder(@NonNull Context context) {
        return new Builder(context);
    }

    public static Builder Builder(@NonNull Context context, int numberOfItems) {
        return new Builder(context, numberOfItems);
    }

    public static final class Builder {
        private SparseArray<SpeedDialMenuItem> mMenuItems;
        private int mMenuItemsLatestPosition;
        private Context mContext;
        private Typeface mCustomTypeface;
        private int mCustomBackgroundColor = Integer.MAX_VALUE;
        private @ColorInt int mCustomIconsColor = Integer.MAX_VALUE;
        private boolean mEnableIconRotating = false;

        private Builder(@NonNull Context context) {
            mContext = context;
            mMenuItems = new SparseArray<>();
            mMenuItemsLatestPosition = 0;
        }

        private Builder(@NonNull Context context, int numberOfItems) {
            mContext = context;
            mMenuItems = new SparseArray<>(numberOfItems);
            mMenuItemsLatestPosition = 0;
        }

        public Builder addMenuItem(@NonNull String label, @NonNull IIcon icon) {
            IconicsDrawable menuItemIcon = new IconicsDrawable(mContext, icon);
            if (mCustomIconsColor != Integer.MAX_VALUE)
                menuItemIcon.color(mCustomIconsColor);
            SpeedDialMenuItem newItem = new SpeedDialMenuItem(mContext, menuItemIcon, label);
            mMenuItems.put(mMenuItemsLatestPosition, newItem);
            ++mMenuItemsLatestPosition;
            return this;
        }

        public Builder addMenuItem(@NonNull SpeedDialMenuItem menuItem) {
            mMenuItems.put(mMenuItemsLatestPosition, menuItem);
            ++mMenuItemsLatestPosition;
            return this;
        }

        public Builder addAll(@NonNull SpeedDialMenuItem... menuItems) {
            for (SpeedDialMenuItem menuItem : menuItems) {
                mMenuItems.put(mMenuItemsLatestPosition, menuItem);
                ++mMenuItemsLatestPosition;
            }
            return this;
        }

        public Builder setLabelsCustomTypeface(@FontRes int customFont) {
            mCustomTypeface = ResourcesCompat.getFont(mContext, customFont);
            return this;
        }

        /**
         * Sets custom background color for labels. If not specified, uses [255, 192, 192, 192]
         * @param alpha Alpha component of the color | [0, ..., 255]
         * @param red Red component of the color | [0, ..., 255]
         * @param green Green component of the color |  [0, ..., 255]
         * @param blue Blue component of the color | [0, ..., 255]
         * @return Builder class instance (itself)
         */
        public Builder setLabelsCustomBackgroundColor(int alpha, int red, int green, int blue) {
            mCustomBackgroundColor = Color.argb(alpha, red, green, blue);
            return this;
        }

        public Builder setLabelsCustomBackgroundColor(@ColorInt int color) {
            mCustomBackgroundColor = color;
            return this;
        }

        public Builder setLabelsCustomBackgroundColorRes(@ColorRes int color) {
            mCustomBackgroundColor = ResourcesCompat
                    .getColor(mContext.getResources(), color, null);
            return this;
        }

        public Builder setCustomIconsColor(@ColorInt int color) {
            mCustomIconsColor = color;
            if (mMenuItems.size() > 0) {
                for (int i = 0; i < mMenuItems.size(); ++i) {
                    SpeedDialMenuItem menuItem = mMenuItems.get(i);
                    IconicsDrawable icon = IconicsDrawable.class.cast(menuItem.getIcon());
                    icon.color(color);
                }
            }
            return this;
        }

        public Builder withIconRotationEnabled(boolean isIconRotationEnabled) {
            mEnableIconRotating = isIconRotationEnabled;
            return this;
        }

        public FloatingActionButtonMenuAdapter build() {
            return new FloatingActionButtonMenuAdapter(mMenuItems,
                    mCustomTypeface,
                    mCustomBackgroundColor,
                    mEnableIconRotating);
        }
    }
}
