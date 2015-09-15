package com.jalcdeveloper.weaconapp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jalcdeveloper.weaconapp.R;
import com.jalcdeveloper.weaconapp.database.Sensor;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class WeaconPresenter extends Presenter {

    private static final String TAG = WeaconPresenter.class.getSimpleName();
    private static int CARD_WIDTH = 200;
    private static int CARD_HEIGHT = 200;
    private static Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        Log.d(TAG, "onCreateViewHolder");

        mContext = viewGroup.getContext();
        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        ((TextView)cardView.findViewById(R.id.content_text)).setTextColor(Color.LTGRAY);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        Sensor sensor = (Sensor) o;
        //Ejemplo en http://androidtv-codelabs.appspot.com/static/codelabs/1-androidtv-adding-leanback/#3 -> Create Picasso Target
        ((ViewHolder) viewHolder).mCardView.setTitleText(sensor.get_nombre());
        ((ViewHolder) viewHolder).mCardView.setContentText(sensor.get_descripcion());
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        //TODO: Cargar una imagen u otra segun el tipo de sensor y estado
        //Imagenes libres en https://icons8.com
        String imageUri = "drawable://" + R.drawable.light_on;
        ((ViewHolder) viewHolder).updateCardViewImage(imageUri);

    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    //He tenido que ponerlo static porque me daba error el Studio (linea 92)
    public static class PicassoImageCardViewTarget implements Target {
        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView mImageCardView) {
            this.mImageCardView = mImageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mImageCardView.setMainImage(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

    static class ViewHolder extends Presenter.ViewHolder {
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;

        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext
                    .getResources()
                    .getDrawable(R.drawable.light_off);
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        protected void updateCardViewImage(String url) {
           Picasso.with(mContext)
                    .load(url)
                    .resize(CARD_WIDTH * 2, CARD_HEIGHT * 2)
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(mImageCardViewTarget);
        }
    }

}
