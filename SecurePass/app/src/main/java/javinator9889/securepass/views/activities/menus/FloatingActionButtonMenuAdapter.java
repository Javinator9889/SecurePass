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

    /**
     * Adapter used for creating the FloatingActionButton's menu.
     *
     * @param menuItems             the items.
     * @param customTypeface        if applied, a custom typeface (use {@code null} for no custom
     *                              typeface).
     * @param customBackgroundColor if applied, a custom background (use {@code Integer
     *                              .MAX_VALUE} for no custom background).
     * @param enableIconRotating    enables or not icon rotation.
     */
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

    /**
     * Builder method for generating a new instance.
     *
     * @param context the context from which the menu/button will be created.
     * @return {@code Builder} instance.
     */
    public static Builder Builder(@NonNull Context context) {
        return new Builder(context);
    }

    /**
     * Builder method for generating a new instance.
     *
     * @param context       the context from which the menu/button will be created.
     * @param numberOfItems the amount of items that the menu will use - better if this number is
     *                      always the same.
     * @return {@code Builder} instance.
     */
    public static Builder Builder(@NonNull Context context, int numberOfItems) {
        return new Builder(context, numberOfItems);
    }

    /**
     * Gets the count of the amount of menu items.
     *
     * @return {@code int} with the amount of elements.
     */
    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    /**
     * Obtains the menu item at the provided key.
     *
     * @param context not used.
     * @param key     the key from which obtaining the item.
     * @return {@code SpeedDialMenuItem} which can be {@code null} if not found.
     */
    @Nullable
    @Override
    public SpeedDialMenuItem getMenuItem(@Nullable Context context, int key) {
        return mMenuItems.get(key, null);
    }

    /**
     * Returns the rotation degrees when FloatingActionButton is clicked.
     *
     * @return {@code float} with the rotation degrees.
     */
    @Override
    public float fabRotationDegrees() {
        return mEnableIconRotating ? 135F : 0F;
    }

    /**
     * Apply formatting to the `TextView` used for the label of the menu item at the given position.
     * Note: positions start at zero closest to the FAB and increase for items further away.
     *
     * @param context  not used.
     * @param position not used.
     * @param label    the label that will be prepared.
     */
    @Override
    public void onPrepareItemLabel(@Nullable Context context,
                                   int position,
                                   @NotNull TextView label) {
        label.setTypeface(mCustomTypeface);
    }

    /**
     * Obtains the background color at the specified position or the custom background color.
     *
     * @param position the position from which obtaining the background color when no custom one
     *                 is used.
     * @return {@code int} with the color.
     */
    @Override
    public int getBackgroundColour(int position) {
        if (!mIsCustomBackgroundColorEnabled)
            return super.getBackgroundColour(position);
        else
            return mCustomBackgroundColor;
    }

    /**
     * Builder class for {@link FloatingActionButtonMenuAdapter}.
     */
    public static final class Builder {
        private SparseArray<SpeedDialMenuItem> mMenuItems;
        private int mMenuItemsLatestPosition;
        private Context mContext;
        private Typeface mCustomTypeface;
        private int mCustomBackgroundColor = Integer.MAX_VALUE;
        private @ColorInt
        int mCustomIconsColor = Integer.MAX_VALUE;
        private boolean mEnableIconRotating = false;

        /**
         * Private constructor.
         *
         * @param context the context from which the menu/button will be created.
         */
        private Builder(@NonNull Context context) {
            mContext = context;
            mMenuItems = new SparseArray<>();
            mMenuItemsLatestPosition = 0;
        }

        /**
         * Private constructr.
         *
         * @param context       the context from which the menu/button will be created.
         * @param numberOfItems the amount of items that the menu will use - better if this number is
         *                      always the same.
         */
        private Builder(@NonNull Context context, int numberOfItems) {
            mContext = context;
            mMenuItems = new SparseArray<>(numberOfItems);
            mMenuItemsLatestPosition = 0;
        }

        /**
         * Adds a menu item with the specified label and icon.
         *
         * @param label the item label.
         * @param icon  the item icon.
         * @return {@code self}.
         */
        public Builder addMenuItem(@NonNull String label, @NonNull IIcon icon) {
            IconicsDrawable menuItemIcon = new IconicsDrawable(mContext, icon);
            if (mCustomIconsColor != Integer.MAX_VALUE)
                menuItemIcon.color(mCustomIconsColor);
            SpeedDialMenuItem newItem = new SpeedDialMenuItem(mContext, menuItemIcon, label);
            mMenuItems.put(mMenuItemsLatestPosition, newItem);
            ++mMenuItemsLatestPosition;
            return this;
        }

        /**
         * Adds a menu item which has been created.
         *
         * @param menuItem the menu item that was created.
         * @return {@code self}.
         */
        public Builder addMenuItem(@NonNull SpeedDialMenuItem menuItem) {
            mMenuItems.put(mMenuItemsLatestPosition, menuItem);
            ++mMenuItemsLatestPosition;
            return this;
        }

        /**
         * Adds an amount of created menu items.
         *
         * @param menuItems the menu items that were created.
         * @return {@code self}.
         */
        public Builder addAll(@NonNull SpeedDialMenuItem... menuItems) {
            for (SpeedDialMenuItem menuItem : menuItems) {
                mMenuItems.put(mMenuItemsLatestPosition, menuItem);
                ++mMenuItemsLatestPosition;
            }
            return this;
        }

        /**
         * Sets custom typeface for the menu items' labels.
         *
         * @param customFont the custom typeface ({@code FontRes}).
         * @return {@code self}.
         */
        public Builder setLabelsCustomTypeface(@FontRes int customFont) {
            mCustomTypeface = ResourcesCompat.getFont(mContext, customFont);
            return this;
        }

        /**
         * Sets custom background color for labels. If not specified, uses [255, 192, 192, 192]
         *
         * @param alpha Alpha component of the color | [0, ..., 255]
         * @param red   Red component of the color | [0, ..., 255]
         * @param green Green component of the color |  [0, ..., 255]
         * @param blue  Blue component of the color | [0, ..., 255]
         * @return Builder class instance (itself)
         */
        public Builder setLabelsCustomBackgroundColor(int alpha, int red, int green, int blue) {
            mCustomBackgroundColor = Color.argb(alpha, red, green, blue);
            return this;
        }

        /**
         * Sets custom background color for labels.
         *
         * @param color the color int.
         * @return {@code self}.
         */
        public Builder setLabelsCustomBackgroundColor(@ColorInt int color) {
            mCustomBackgroundColor = color;
            return this;
        }

        /**
         * Sets custom background color for labels.
         *
         * @param color the color resource.
         * @return {@code self}.
         */
        public Builder setLabelsCustomBackgroundColorRes(@ColorRes int color) {
            mCustomBackgroundColor = ResourcesCompat
                    .getColor(mContext.getResources(), color, null);
            return this;
        }

        /**
         * Sets custom color for icons.
         *
         * @param color the color int.
         * @return {@code self}.
         */
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

        /**
         * Enables (or disables) the rotation when FloatingActionButton is clicked.
         *
         * @param isIconRotationEnabled whether it is enabled or not.
         * @return {@code self}.
         */
        public Builder withIconRotationEnabled(boolean isIconRotationEnabled) {
            mEnableIconRotating = isIconRotationEnabled;
            return this;
        }

        /**
         * Generates a new instance of {@link FloatingActionButtonMenuAdapter} class with
         * predefined options.
         *
         * @return {@code FloatingActionButtonMenuAdapter} instance.
         */
        public FloatingActionButtonMenuAdapter build() {
            return new FloatingActionButtonMenuAdapter(mMenuItems,
                    mCustomTypeface,
                    mCustomBackgroundColor,
                    mEnableIconRotating);
        }
    }
}
