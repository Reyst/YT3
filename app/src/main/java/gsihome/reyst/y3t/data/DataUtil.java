package gsihome.reyst.y3t.data;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import gsihome.reyst.y3t.R;

public class DataUtil {

    private static final int MAGIC_N1 = 10_000_000;

    private static DateFormat sFormatter;

    public static List<IssueEntity> getModel(Context ctx, State state) {

        String[] urls = ctx.getResources().getStringArray(R.array.image_urls);

        List<IssueEntity> result = new ArrayList<>(10);

        Random r = new Random(System.currentTimeMillis());

        for (int i = 1; i <= 10; i++) {

            int randomInt = r.nextInt(MAGIC_N1);
            String number = String.format(Locale.getDefault(),"CE-%d08", randomInt);

            String category = "";
            String responsible = "";
            int iconId = 0;

            switch (randomInt % 2) {
                case 0:
                    category = ctx.getString(R.string.category1);
                    responsible = ctx.getString(R.string.responsible1);
                    iconId = R.drawable.communal_service;
                    break;
                case 1:
                    category = ctx.getString(R.string.category2);
                    responsible = ctx.getString(R.string.responsible2);
                    iconId = R.drawable.building_and_upgrade;
                    break;
            }

            Date dtCreated = new Date();
            dtCreated.setDate(r.nextInt(31) + 1);
            dtCreated.setMonth(1);

            Date dtReg = new Date();
            dtReg.setDate(r.nextInt(31) + 1);
            dtReg.setMonth(2);

            result.add(new IssueEntity(i, number, category, state, dtCreated, dtReg, new Date(),
                    responsible, iconId, randomInt % 3,
                    ctx.getString(R.string.descr_task_part1) + number + ctx.getString(R.string.descr_task_part2) + number,
                    Arrays.asList(urls)));
        }

        return result;
    }

    public static DateFormat getFormatter() {

        if (sFormatter == null) {
            sFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        }

        return sFormatter;
    }
}
