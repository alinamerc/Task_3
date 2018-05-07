package com.zhirova.presentation.screen.start;


import android.content.Context;
import com.zhirova.model.NewsModel;
import com.zhirova.model.NewsModelImpl;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class StartPresenter implements StartContract.Presenter {

    private final String TAG = "START_PRESENTER";
    private StartContract.View view;
    private NewsModel newsModel;
    private CompositeDisposable disposables;


    @Override
    public void subscribe(StartContract.View view, boolean needUpdate, Context context) {
        this.view = view;
        newsModel = new NewsModelImpl(context);
        disposables = new CompositeDisposable();
        updateScreen(needUpdate);
    }


    @Override
    public void unsubsribe(StartContract.View view) {
        this.view = null;
        disposables.dispose();
    }


    @Override
    public void refreshNews() {
        updateScreen(true);
    }


    private void updateScreen(boolean needUpdate) {
        if (view == null) return;
        Disposable d = newsModel.getNews(needUpdate)
                .subscribe(dataContainer -> {
                    if (dataContainer.getData().size() == 0){
                        if (dataContainer.isLocal()){
                            view.showLoader();
                        } else {
                            view.showInfoAboutLackOfNews();
                        }
                    } else {
                        view.updateNewsList(dataContainer.getData());
                    }
                }, throwable -> {
//                    if (throwable instanceof ServerException){
//                        view.showServerError();
//                    } else if (throwable instanceof InternetException) {
//                        view.showInternetError();
//                    } else {
//                        view.showError();
//                    }
                });
        disposables.add(d);
    }


}
