package arrow.mems.util;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.persistence.util.Condition;
import arrow.framework.persistence.util.Condition.Junction;
import arrow.mems.bean.data.RememberSearchCriteria;
import arrow.mems.bean.data.SearchCriteria;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.CompareOperatorConstants;
import arrow.mems.persistence.entity.SearchCondition;
import arrow.mems.util.string.ArrowStrUtils;


public class QueryByCompareOperatorUtils {

  public static String queryCondition(String columnname, final int compareType) {

    String outputQuery = StringUtils.EMPTY;
    switch (compareType) {
      case CommonConstants.PULLDOWN_EQUAL_TYPE:
        outputQuery =
        CompareOperatorConstants.UPPER + CompareOperatorConstants.OPEN_BRACKET + columnname + CompareOperatorConstants.CLOSE_BRACKET + CompareOperatorConstants.Equal + CompareOperatorConstants.paramOperator;
        break;
      case CommonConstants.PULLDOWN_NOT_EQUAL_TYPE:
        outputQuery =
            CompareOperatorConstants.UPPER + CompareOperatorConstants.OPEN_BRACKET + columnname + CompareOperatorConstants.CLOSE_BRACKET + CompareOperatorConstants.NotEqual + CompareOperatorConstants.paramOperator;
        break;
      case CommonConstants.PULLDOWN_GREATER_THAN_TYPE:
        outputQuery =
            CompareOperatorConstants.UPPER + CompareOperatorConstants.OPEN_BRACKET + columnname + CompareOperatorConstants.CLOSE_BRACKET + CompareOperatorConstants.GreaterThan + CompareOperatorConstants.paramOperator;
        break;
      case CommonConstants.PULLDOWN_LESS_THAN_TYPE:
        outputQuery =
            CompareOperatorConstants.UPPER + CompareOperatorConstants.OPEN_BRACKET + columnname + CompareOperatorConstants.CLOSE_BRACKET + CompareOperatorConstants.LessThan + CompareOperatorConstants.paramOperator;
        break;
      case CommonConstants.PULLDOWN_GREATER_THAN_OR_EQUAL_TYPE:
        outputQuery =
            CompareOperatorConstants.UPPER + CompareOperatorConstants.OPEN_BRACKET + columnname + CompareOperatorConstants.CLOSE_BRACKET + CompareOperatorConstants.GreaterThanOrEqual + CompareOperatorConstants.paramOperator;
        break;
      case CommonConstants.PULLDOWN_LESS_THAN_OR_EQUAL_TYPE:
        outputQuery =
            CompareOperatorConstants.UPPER + CompareOperatorConstants.OPEN_BRACKET + columnname + CompareOperatorConstants.CLOSE_BRACKET + CompareOperatorConstants.LessThanOrEqual + CompareOperatorConstants.paramOperator;
        break;
      case CommonConstants.PULLDOWN_CONTAIN_STRING_TYPE:
        outputQuery =
            CompareOperatorConstants.UPPER + CompareOperatorConstants.OPEN_BRACKET + columnname + CompareOperatorConstants.CLOSE_BRACKET + CompareOperatorConstants.ContainString + CompareOperatorConstants.paramOperator;
        break;
      case CommonConstants.PULLDOWN_CONTAIN_STRING_TYPE_EXACT_CASE:
        outputQuery = columnname + CompareOperatorConstants.ContainStringExactCase + CompareOperatorConstants.paramOperator;
        break;
      default:
        break;
    }

    return outputQuery;
  }

  public static Object queryParam(String param, final int compareType) {

    Object outputQuery = null;
    switch (compareType) {
      case CommonConstants.PULLDOWN_EQUAL_TYPE:
        outputQuery = param.toUpperCase();
        break;
      case CommonConstants.PULLDOWN_NOT_EQUAL_TYPE:
        outputQuery = param.toUpperCase();
        break;
      case CommonConstants.PULLDOWN_GREATER_THAN_TYPE:
        outputQuery = param.toUpperCase();
        break;
      case CommonConstants.PULLDOWN_LESS_THAN_TYPE:
        outputQuery = param.toUpperCase();
        break;
      case CommonConstants.PULLDOWN_GREATER_THAN_OR_EQUAL_TYPE:
        outputQuery = param.toUpperCase();
        break;
      case CommonConstants.PULLDOWN_LESS_THAN_OR_EQUAL_TYPE:
        outputQuery = param.toUpperCase();
        break;
      case CommonConstants.PULLDOWN_CONTAIN_STRING_TYPE:
        outputQuery = ArrowStrUtils.likePattern(ArrowStrUtils.nullTrim(param.toUpperCase()));
        break;
      case CommonConstants.PULLDOWN_CONTAIN_STRING_TYPE_EXACT_CASE:
        outputQuery = ArrowStrUtils.likePatternExactCase(param);
        break;
      default:
        break;
    }
    return outputQuery;
  }

  public static List<SearchCriteria> getListSearchCondition(SearchCondition condition) {

    final List<SearchCriteria> pListSearchConditions = new ArrayList<SearchCriteria>();
    final String textCondition = condition.getCondition();
    final String[] conditionsSplit = textCondition.split(CompareOperatorConstants.KEY_SEPARATION);

    int type = 1;
    int operator = 1;
    String param = "";

    for (int eachParam = 0; eachParam < conditionsSplit.length; eachParam++) {
      final int eachCondition = eachParam + 4;
      switch (eachCondition % 4) {
        case 0:
          type = Integer.parseInt(conditionsSplit[eachParam]);
          break;
        case 1:
          operator = Integer.parseInt(conditionsSplit[eachParam]);
          break;
        case 2:
          param = conditionsSplit[eachParam];
          break;
        default:
          break;
      }

      // If have 4 parameter, create one condition.
      if ((eachParam >= 3) && (((eachParam + 1) % 4) == 0)) {
        final SearchCriteria searchGen = new SearchCriteria();
        searchGen.setType(type);
        searchGen.setOperator(operator);
        searchGen.setParam(param);
        pListSearchConditions.add(searchGen);
      }
    }

    return pListSearchConditions;
  }

  public static RememberSearchCriteria getRememberSearchCondition(SearchCondition condition) {
    final RememberSearchCriteria rememberSearchCriteria = new RememberSearchCriteria();
    final String textCondition = condition.getCondition();
    final String[] conditionsSplit = textCondition.split(CompareOperatorConstants.KEY_SEPARATION);


    rememberSearchCriteria.setCompareSearchType(Integer.parseInt(conditionsSplit[3]));
    rememberSearchCriteria.setRememberSearchContain(condition.getLabel());
    rememberSearchCriteria.setShareCondition((!(condition.getOfficeCode() == null) && !condition.getOfficeCode().isEmpty()));

    return rememberSearchCriteria;
  }

  public static SearchCondition createNewConditions(RememberSearchCriteria rememberSearchCriteria, UserCredential userCredential,
      List<SearchCriteria> pListSearchConditions) {

    final SearchCondition searchCondition = new SearchCondition();

    searchCondition.setCreatorDisplay(userCredential.getUserId());
    if (rememberSearchCriteria.isShareCondition()) {
      searchCondition.setOfficeCode(userCredential.getOfficeCode());
    }
    searchCondition.setLabel(rememberSearchCriteria.getRememberSearchContain());

    // First version
    searchCondition.setCondFormatVer(0);
    final StringBuilder conditions = new StringBuilder();

    for (final SearchCriteria criteria : pListSearchConditions) {
      final String conditionEach =
          MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}{7}", criteria.getType(), CompareOperatorConstants.KEY_SEPARATION, criteria.getOperator(),
              CompareOperatorConstants.KEY_SEPARATION, criteria.getParam(), CompareOperatorConstants.KEY_SEPARATION,
              rememberSearchCriteria.getCompareSearchType(), CompareOperatorConstants.KEY_SEPARATION);
      conditions.append(conditionEach);
    }
    searchCondition.setCondition(conditions.toString());
    searchCondition.setCondFormatVer(0);

    return searchCondition;

  }

  public static Junction createJunction(RememberSearchCriteria rememberSearchCriteria) {

    if (rememberSearchCriteria.getCompareSearchType() == CommonConstants.PULLDOWN_ALL_TYPE)
      return new Condition.Conjunction();
    else
      return new Condition.Disjunction();
  }
}
